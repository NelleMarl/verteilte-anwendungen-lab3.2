package de.berlin.htw.boundary.dto;

import jakarta.validation.constraints.*;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
public class Item {

	@NotNull
	@Size(max = 255, message = "Der Artikelname darf maximal 255 Zeichen lang sein.")
	private String productName;

	@NotNull
	@Pattern(regexp = "\\d-\\d-\\d-\\d-\\d-\\d", message = "Die Artikelnummer muss im Format '1-2-3-4-5-6' sein.")
	private String productId;

	@NotNull
	@Min(value = 10, message = "Der Preis muss mindestens 10 Euro betragen.")
	@Max(value = 100, message = "Der Preis darf maximal 100 Euro betragen.")
	private Float price;

	@NotNull
	@Min(value = 1, message = "Die Anzahl muss mindestens 1 betragen.")
	private Integer count;
    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }
    
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
