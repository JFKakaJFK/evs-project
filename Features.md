# Features

## 1. User management - BIS 2019 √

- [x] User entity und Service anpassen (@Chris)
- [x] PasswordEncoder einfügen und testUser anpassen (@Chris)
- [x] [addUserController](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/java/at/qe/sepm/skeleton/ui/controllers/AddUserController.java) anpassen (@Markus @Melanie)
- [x] [users.xhtml](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/webapp/admin/users.xhtml) anpassen und [formular](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/webapp/admin/create-user.xhtml) einfügen (@Andi)

### TODO/Notes

- cNumber Getters und Setters müssen so geschrieben werden weil JSF diese sonst nicht erkennt
- use testing
- navbar anpassen (brauchen wir die tests?)
- sollen in den views alle ROLES angezeigt werden oder nur die mit am meisten rechte?

## 2. Equipment management & Opening Hours - BIS ENDE FERIEN

- [x] Equipment Entity und Service (@Johannes)
- [x] testEquipments hinzufügen (@Johannes)
- [x] EquipmentListController (@Melanie @Markus)
- [x] addEquipmentController (@Melanie @Markus)
- [x] view zur Übersicht aller Equipments und equipment hinzufügen **ADMIN** (@Andi)

- [x] OpeningHours Entity und Service (@Chris)
- [x] testÖffnungszeiten hinzufügen (@Chris)
- [ ] view zur Einsicht der Öffnungszeiten und zum ändern **ADMIN** (@Andi)
- [ ] view zum Ansehen der equipmentDetails(Kommentare, Manuals) **?ALLe? = STUDENT** und Kommentare/Manuals hinzufügen/entfernen nur für **ADMIN**

### TODO/Notes

- Bug: beim ändern des eqipments wird die maximale ausleihdauer auf 0 gesetzt wenn man diese nicht verändert

## 3. Simple Reservations - BIS ENDE FERIEN

- [ ] Reservation Entity (@Johannes)
- [ ] testReservationen (@Johannes)
- [ ] ReservationListController (@Melanie @Markus)
- [ ] addReservationController (@Melanie @Markus)
- [ ] Bestätigungsmails (@Melanie @Markus)
- [ ] view zur Einsicht(und löschen) aller eigenen Reservierungen und reservierung hinzufügen **STUDENT** (@Andi)
- [ ] view zur Einsicht(und löschen) aller reservierungen **ADMIN** (@Andi)
- [ ] view zur Einsicht aller ausgeliehenen und überfälligen Equipments(und Rückgabebestätigungen) **ADMIN** (@Andi)

## 4. EquipmentGroup management - BIS ~ 10.

- [ ] equipmentgroup entity (@Johannes)
- [ ] testEquipmentGroups (@Johannes)
- [ ] EquipmentGroupListController (@Melanie @Markus)
- [ ] AddEquipmentGroupController (@Melanie @Markus)
- [ ] view zur Einsicht, erstellen & löschen der eigenen EquipmentGroups **EMPLOYEE** (@Andi)
- [ ] view zur Einsicht und löschung aller EquipmentGroups **ADMIN** (@Andi)

## 5. EquipmentGroup reservations - BIS 15.

- [ ] AddEquipmentGroupReservationController (@Melanie @Markus)
- [ ] EquipmentGroupListController (@Melanie @Markus)
- [ ] view zur Einsicht, erstellen & löschen der eigenen Reservierungen **EMPLOYEE** (@Andi)
- [ ] view zur Einsicht aller EquipmentGroupReservierungen(und löschen) **ADMIN** (@Andi)
- [ ] view zur Einsicht aller ausgeliehenen und überfälligen Reservierungen(und Rckgabebestätigung) **ADMIN** (@Andi)

## 6. Testing & Bugfixes

- [ ] test entities für alles und dokumentation
- [ ] vagrantfile updaten
