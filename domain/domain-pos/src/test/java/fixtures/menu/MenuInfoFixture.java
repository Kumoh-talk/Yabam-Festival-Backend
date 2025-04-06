package fixtures.menu;

import domain.pos.menu.entity.MenuInfo;

public class MenuInfoFixture {
	public static final Long GENERAL_MENU_ID = 1L;
	public static final int GENERAL_SORT_ORDER = 1;
	public static final String GENERAL_MENU_NAME = "menu name";
	public static final int GENERAL_PRICE = 10000;
	public static final String GENERAL_DESCRIPTION = "menu description";
	public static final String GENERAL_IMAGE_URL = "imageURL";
	public static final boolean GENERAL_IS_SOLD_OUT = false;

	public static MenuInfo REQUEST_MENU_INFO() {
		return MenuInfo.builder()
			.menuName(GENERAL_MENU_NAME)
			.sortOrder(GENERAL_SORT_ORDER)
			.price(GENERAL_PRICE)
			.description(GENERAL_DESCRIPTION)
			.imageUrl(GENERAL_IMAGE_URL)
			.isSoldOut(GENERAL_IS_SOLD_OUT)
			.build();
	}

	public static MenuInfo GENERAL_MENU_INFO() {
		return MenuInfo.builder()
			.menuId(GENERAL_MENU_ID)
			.sortOrder(GENERAL_SORT_ORDER)
			.menuName(GENERAL_MENU_NAME)
			.price(GENERAL_PRICE)
			.description(GENERAL_DESCRIPTION)
			.imageUrl(GENERAL_IMAGE_URL)
			.isSoldOut(GENERAL_IS_SOLD_OUT)
			.build();
	}

	public static MenuInfo CUSTOM_MENU_INFO(Long menuId, int sortOrder, String menuName, int price, String description,
		String imageUrl, boolean isSoldOut) {
		return MenuInfo.builder()
			.menuId(menuId)
			.sortOrder(sortOrder)
			.menuName(menuName)
			.price(price)
			.description(description)
			.imageUrl(imageUrl)
			.isSoldOut(isSoldOut)
			.build();
	}

	public static MenuInfo REQUEST_TO_ENTITY(Long menuId, MenuInfo requestMenuInfo) {
		return MenuInfo.builder()
			.menuId(menuId)
			.menuName(requestMenuInfo.getMenuName())
			.price(requestMenuInfo.getPrice())
			.description(requestMenuInfo.getDescription())
			.imageUrl(requestMenuInfo.getImageUrl())
			.isSoldOut(requestMenuInfo.isSoldOut())
			.build();
	}
}
