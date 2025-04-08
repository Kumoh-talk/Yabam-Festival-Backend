package domain.pos.table.repository;

import java.util.List;
import java.util.Optional;

import domain.pos.store.entity.Store;
import domain.pos.table.entity.Table;

public interface TableRepository {
	List<Table> createTablesAll(Store responStore, Integer queryTableNumber);

	boolean existsTableByStore(Store responStore, Integer queryTableNumber);

	Optional<Table> findById(Long queryTableId);

	Table changeTableActiveStatus(boolean isActive, Table savedInActiveTable);
}
