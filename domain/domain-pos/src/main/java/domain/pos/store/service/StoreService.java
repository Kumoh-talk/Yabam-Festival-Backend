package domain.pos.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.pos.member.entity.Owner;
import domain.pos.member.implement.OwnerValidator;
import domain.pos.store.entity.StoreInfo;
import domain.pos.store.implement.StoreWriter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final OwnerValidator ownerValidator;
	private final StoreWriter storeWriter;

	@Transactional
	public Long createStore(final Owner owner, final StoreInfo createRequestStoreInfo) {
		ownerValidator.validateOwner(owner);

		final Long savedStoreId = storeWriter.createStore(owner, createRequestStoreInfo);
		return savedStoreId;
	}
}
