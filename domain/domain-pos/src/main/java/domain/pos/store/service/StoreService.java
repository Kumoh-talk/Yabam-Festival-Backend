package domain.pos.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.pos.member.entity.Owner;
import domain.pos.member.implement.OwnerValidator;
import domain.pos.store.entity.Store;
import domain.pos.store.implement.StoreWriter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final OwnerValidator ownerValidator;
	private final StoreWriter storeWriter;

	@Transactional
	public Long createStore(Owner owner, Store createRequestStore) {
		ownerValidator.validateOwner(owner);

		Long savedStoreId = storeWriter.createStore(owner, createRequestStore);
		return savedStoreId;
	}
}
