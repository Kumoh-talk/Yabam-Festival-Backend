package domain.pos.menu.implement;

import org.springframework.stereotype.Component;

import domain.pos.menu.entity.MenuCategory;
import domain.pos.menu.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuCategoryWriter {
	private final MenuCategoryRepository menuCategoryRepository;

	public MenuCategory postMenuCategory(String categoryName) {
		return menuCategoryRepository.postMenuCategory(categoryName);
	}
}
