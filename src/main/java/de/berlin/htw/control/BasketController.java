package de.berlin.htw.control;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import de.berlin.htw.boundary.dto.Basket;
import de.berlin.htw.boundary.dto.Item;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.list.ListCommands;

import java.util.List;

@Dependent
public class BasketController {

    @Inject
    protected RedisDataSource redisDS;

    protected ListCommands<String, Item> basketCommands;

    @PostConstruct
    protected void init() {
        basketCommands = redisDS.list(Item.class);
    }

    public Basket getBasket(String userId) {
        List<Item> items = basketCommands.lrange("basket:" + userId, 0, -1);
        Basket basket = new Basket();
        basket.setItems(items);
        return basket;
    }

    public void addItemToBasket(String userId, Item item) {
        String basketKey = "basket:" + userId;

        // Fetch current basket
        List<Item> currentItems = basketCommands.lrange(basketKey, 0, -1);

        // Debugging: Anzahl der Artikel prüfen
        System.out.println("Aktuelle Artikelanzahl: " + currentItems.size());

        // Validate basket size
        if (currentItems.size() >= 10) {
            throw new BadRequestException("The basket cannot contain more than 10 items.");
        }

        // Check for duplicates
        boolean itemExists = currentItems.stream().anyMatch(existingItem -> existingItem.getProductId().equals(item.getProductId()));
        if (itemExists) {
            throw new BadRequestException("Item already exists in the basket.");
        }

        // Add new item to the basket
        basketCommands.rpush(basketKey, item);
    }

    public void clearBasket(String userId) {
        basketCommands.ltrim("basket:" + userId, 1, 0);
    }
}
