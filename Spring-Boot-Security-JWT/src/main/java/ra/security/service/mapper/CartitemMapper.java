//package ra.security.service.mapper;
//
//import org.springframework.stereotype.Component;
//import ra.security.model.domain.CartItem;
//import ra.security.model.dto.request.CartItemRequest;
//import ra.security.model.dto.response.CartItemResponse;
//import ra.security.service.IGenericMapper;
//@Component
//public class CartitemMapper implements IGenericMapper<CartItem, CartItemRequest, CartItemResponse> {
//    @Override
//    public CartItem toEntity(CartItemRequest cartItemRequest) {
//        return CartItem.builder()
//                .product(cartItemRequest.getProduct())
//                .price(cartItemRequest.getPrice())
//                .quantity(cartItemRequest.getQuantity())
//                .build();
//    }
//
//    @Override
//    public CartItemResponse toResponse(CartItem cartItem) {
//        return CartItemResponse.builder()
//                .idCart(cartItem.getIdCart())
//                .product(cartItem.getProduct())
//                .price(cartItem.getPrice())
//                .quantity(cartItem.getQuantity())
//                .build();
//    }
//}
