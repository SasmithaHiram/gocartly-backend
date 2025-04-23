package edu.icet.service;

import edu.icet.dto.Inventory;

import java.util.List;

public interface InventoryService {
    boolean create(Inventory inventory);
    Inventory searchById(Long id);
    boolean update(Inventory inventory);
    Boolean delete(Long id);
    List<Inventory> getAll();

}
