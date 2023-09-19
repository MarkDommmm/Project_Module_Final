package ra.security.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.security.exception.CustomException;
import ra.security.model.domain.CartItem;
import ra.security.model.dto.request.CartItemRequest;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.service.IGenericMapper;
import ra.security.service.impl.ProductService;

@Component
public class CartMapper implements IGenericMapper<CartItem, CartItemRequest, CartItemResponse> {
  @Autowired
  private ProductService productService;
    @Override
    public CartItem toEntity(CartItemRequest cartItemRequest) {
        return CartItem.builder()
                .product(cartItemRequest.getProduct())
                .quantity(cartItemRequest.getQuantity())
                .price(cartItemRequest.getPrice())
                .status(cartItemRequest.isStatus())
                .build();
    }

    @Override
    public CartItemResponse toResponse(CartItem cartItem) throws CustomException {
        ProductResponse p = productService.findById(cartItem.getProduct().getId());
        return CartItemResponse.builder()
                .idCart(cartItem.getIdCart())
                .idProduct(p.getId())
                .product_name(p.getName())
                .img(p.getMain_image())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .status(cartItem.isStatus())
                .build();
    }
}
