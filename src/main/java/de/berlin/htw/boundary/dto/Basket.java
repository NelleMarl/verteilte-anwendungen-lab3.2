package de.berlin.htw.boundary.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Basket extends Order {

	@Valid
	@Size(max = 10, message = "Der Warenkorb darf nicht mehr als 10 Artikel enthalten.")
	private List<Item> items;

	private Float remainingBalance;

	@Override
	public List<Item> getItems() {
		return items;
	}

	@Override
	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Float getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(Float remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
}
