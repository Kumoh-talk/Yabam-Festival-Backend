package fixtures.menu;

import domain.pos.menu.entity.MenuInfo;

public class MenuInfoFixture {
	private static final Long GENERAL_MENU_ID = 1L;
	private static final String GENERAL_MENU_NAME = "menu name";
	private static final int GENERAL_PRICE = 10000;
	private static final String GENERAL_DESCRIPTION = "menu description";
	private static final String GENERAL_IMAGEURL = "imageURL";

	public static MenuInfo REQUEST_MENU_INFO() {
		return MenuInfo.builder()
			.menuName(GENERAL_MENU_NAME)
			.price(GENERAL_PRICE)
			.description(GENERAL_DESCRIPTION)
			.imageUrl(GENERAL_IMAGEURL)
			.build();
	}

	public static MenuInfo GENERAL_MENU_INFO() {
		return MenuInfo.builder()
			.menuId(GENERAL_MENU_ID)
			.menuName(GENERAL_MENU_NAME)
			.price(GENERAL_PRICE)
			.description(GENERAL_DESCRIPTION)
			.imageUrl(GENERAL_IMAGEURL)
			.build();
	}

	public static MenuInfo CUSTOM_MENU_INFO(Long menuId) {
		return MenuInfo.builder()
			.menuId(menuId)
			.menuName(GENERAL_MENU_NAME)
			.price(GENERAL_PRICE)
			.description(GENERAL_DESCRIPTION)
			.imageUrl(GENERAL_IMAGEURL)
			.build();
	}

	public static MenuInfo REQUEST_TO_ENTITY(Long menuId, MenuInfo requestMenuInfo) {
		return MenuInfo.builder()
			.menuId(menuId)
			.menuName(requestMenuInfo.getMenuName())
			.price(requestMenuInfo.getPrice())
			.description(requestMenuInfo.getDescription())
			.imageUrl(requestMenuInfo.getImageUrl())
			.build();
	}
}
