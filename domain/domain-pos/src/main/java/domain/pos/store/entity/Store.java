package domain.pos.store.entity;

import domain.pos.member.entity.UserPassport;
import lombok.Getter;

@Getter
public class Store {
	private final Long storeId;
	private final Boolean isOpen;
	private final StoreInfo storeInfo;
	private final UserPassport ownerPassport;

	public Store(Long storeId, Boolean isOpen, StoreInfo storeInfo, UserPassport ownerPassport) {
		this.storeId = storeId;
		this.isOpen = isOpen;
		this.storeInfo = storeInfo;
		this.ownerPassport = ownerPassport;
	}
}
