package at.qe.sepm.skeleton.ui.controllers;


/**
 * @WARNING: This class is copy pasted. I will use this as a template
 * for the booking controller
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("", method = RequestMethod.GET) //Insert Directory
@Api(value = "bookings", description = "Booking resource endpoint")

public class BookingController {

    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Booking> fetchAll() {
        LOG.debug("Get all bookings");
        return bookingService.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Booking fetchById(@PathVariable final Long id) {
        LOG.debug("Fetch booking");
        return bookingService.findOne(id);
    }

    /**
     * Uses idempotent & safe PUT instead of the unsafe POST.
     * <p>
     * Actually application/json media type is support for the enum type.
     * This endpoint was actually tested with the popular Postman REST client, setting the header `Content-Type: application/json`.
     * Apparently Swagger does not support it.
     */
    @RequestMapping(path = "/{id}/status", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Booking updateStatus(@PathVariable final Long id, @RequestBody final Booking.Status status) {
        LOG.debug("Update booking status");
        return bookingService.updateBookingStatus(id, status);
    }

    /**
     * Version that works with Swagger, to satisfy task requirements.
     * Simply delegates to the actual implementation.
     */
    @RequestMapping(path = "/{id}/status-swagger", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Booking updateStatusViaSwaggerClient(@PathVariable final Long id, @RequestBody final String status) {
        final Booking.Status typesafeStatus = mapToTypesafeStatus(status);

        return updateStatus(id, typesafeStatus);
    }

    /**
     * This is actually business logic and should normally be located in the service layer but as this is just
     * a workaround for the Swagger client, this mapping code is located here.
     * <p>
     * However if this service should go in production this code needs to be moved to the (booking) service layer.
     */
    private Booking.Status mapToTypesafeStatus(final String status) {
        final Booking.Status typesafeStatus;
        switch (status) {
            case "OPEN":
                typesafeStatus = Booking.Status.OPEN;
                break;
            case "ACTIVE":
                typesafeStatus = Booking.Status.ACTIVE;
                break;
            case "CANCELLED":
                typesafeStatus = Booking.Status.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException("Provided status does not exist.");
        }
        return typesafeStatus;
    }

    // TODO CreateBookingDto that constraints the fields that can be provided
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Booking save(@RequestBody final Booking booking) {
        LOG.debug("Book");
        return bookingService.book(booking);
    }
}