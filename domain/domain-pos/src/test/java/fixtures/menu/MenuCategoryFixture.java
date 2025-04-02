package fixtures.menu;

import domain.pos.menu.entity.MenuCategory;

public class MenuCategoryFixture {
	private static final Long MENU_CATEGORY_ID = 1L;
	private static final String CATEGORY_NAME = "categoryName";

	public static MenuCategory CUSTOM_MENU_CATEGORY(Long menuCategoryId) {
		return MenuCategory.builder()
			.menuCategoryId(menuCategoryId)
			.menuCategoryName(CATEGORY_NAME)
			.build();
	}

	public static MenuCategory CUSTOM_MENU_CATEGORY(Long menuCategoryId, String categoryName) {
		return MenuCategory.builder()
			.menuCategoryId(menuCategoryId)
			.menuCategoryName(categoryName)
			.build();
	}
}
