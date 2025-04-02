package domain.pos.store.entity;

import java.awt.geom.Point2D;

import lombok.Getter;

@Getter
public class StoreInfo {
	private Long storeId;
	private final String storeName;
	private final Point2D.Double location;
	private final String desciption;
	private final String headImageUrl;
	private final String university;

	public StoreInfo(Long storeId, String storeName, Point2D.Double location, String desciption, String headImageUrl,
		String university) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.location = location;
		this.desciption = desciption;
		this.headImageUrl = headImageUrl;
		this.university = university;
	}

	private StoreInfo(String storeName, Point2D.Double location, String desciption, String headImageUrl,
		String university) {
		this.storeName = storeName;
		this.location = location;
		this.desciption = desciption;
		this.headImageUrl = headImageUrl;
		this.university = university;
	}

	public static StoreInfo of(String storeName, Point2D.Double location, String desciption, String headImageUrl,
		String university) {
		return new StoreInfo(
			storeName,
			location,
			desciption,
			headImageUrl,
			university);
	}
}
