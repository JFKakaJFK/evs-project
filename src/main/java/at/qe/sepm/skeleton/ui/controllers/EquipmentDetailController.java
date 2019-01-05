package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class EquipmentDetailController {

    @Autowired
    private EquipmentService equipmentService;

    private Equipment equipment;

    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
        doReloadEquipment();
    }

    public Equipment getEquipment(){
        return equipment;
    }

    public void doReloadEquipment(){
        equipment = equipmentService.loadEquipment(equipment.getId());
    }

    public void doSaveEquipment(){
        equipment = this.equipmentService.saveEquipmnet(this.equipment);
    }

    public void doDeleteEquipment(){
        this.equipmentService.deleteEquipment(equipment);
        this.equipment = null;
    }
}
