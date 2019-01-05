package at.qe.sepm.skeleton.ui.controllers;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("view")
public class EquipmentListController {
	
	@Autowired
	private EquipmentService equipmentService;
	
	public Collection<Equipment> getEquipment() {
		return equipmentService.getAllEquipment();
		
	}
	
}
