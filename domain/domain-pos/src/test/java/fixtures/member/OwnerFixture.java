package fixtures.member;

import domain.pos.member.entity.Owner;

public class OwnerFixture {
	// 가게 점주 고유 ID
	private static final Long OWNER_ID = 1L;
	private static final Long OWNER_ID_DIFFERENT = 999L;

	public static Owner GENERAL_OWNER() {
		return new Owner(OWNER_ID);
	}

	public static Owner GENERAL_OWNER_DIFFERENT() {
		return new Owner(OWNER_ID_DIFFERENT);
	}

	public static Owner CUSTOM_OWNER(Long ownerId) {
		return new Owner(ownerId);
	}
}
