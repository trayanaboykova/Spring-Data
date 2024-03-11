package org.automappingobjects_exercise.util;

import org.automappingobjects_exercise.service.dto.CartItemDTO;

public interface ShoppingCartService {
    String addItem(CartItemDTO item);
    String deleteItem(CartItemDTO item);
    String buyItem();
}
