package edu.icet.service.impl;

import edu.icet.dto.Inventory;
import edu.icet.entity.InventoryEntity;
import edu.icet.repository.InventoryRepository;
import edu.icet.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean create(Inventory inventory) {
        if (inventory != null) {
            inventoryRepository.save(modelMapper.map(inventory, InventoryEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public Inventory searchById(Long id) {
        if (id != null) {
            modelMapper.map(inventoryRepository.findById(id), Inventory.class);
        }
        return null;
    }

    @Override
    public boolean update(Inventory inventory) {
        if (inventory != null) {
            this.create(inventory);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        if (id != null) {
            inventoryRepository.deleteById(id);

        }
        return null;
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepository.findAll().stream().map(inventoryEntity ->
                modelMapper.map(inventoryEntity, Inventory.class)).toList();
    }

}
