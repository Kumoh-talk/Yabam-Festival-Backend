package domain.pos.menu.service;

import org.springframework.stereotype.Service;

import domain.pos.menu.entity.MenuCategory;
import domain.pos.menu.implement.MenuCategoryWriter;
import domain.pos.store.implement.StoreValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {
	private final StoreValidator storeValidator;
	private final MenuCategoryWriter menuCategoryWriter;

	public MenuCategory postMenuCategory(Long storeId, Long userId, String categoryName) {
		storeValidator.validateStoreOwner(storeId, userId);
		return menuCategoryWriter.postMenuCategory(categoryName);
	}
}
