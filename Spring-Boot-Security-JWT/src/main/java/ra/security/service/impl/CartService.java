package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ra.security.exception.CustomException;
import ra.security.model.domain.CartItem;
import ra.security.model.domain.Discount;
import ra.security.model.domain.Product;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.DiscountResponse;
import ra.security.repository.IDiscountRepsository;
import ra.security.repository.IProductRepository;
import ra.security.service.mapper.CartMapper;
import ra.security.service.mapper.DiscountMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private ProductService productService;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private IDiscountRepsository discountRepsository;

    private List<CartItem> cartItemList = new ArrayList<>();

    public void addDiscount(List<Long> idCount) throws CustomException {
        List<CartItem> newCart = new ArrayList<>();
        List<Discount> discountList = discountRepsository.findAllByIdIn(idCount);
        for (Discount discount : discountList) {
            for (CartItem cartItem : cartItemList) {
                cartItem.setPrice(cartItem.getPrice() - discount.getPromotion_price());
                newCart.add(cartItem);
            }
        }
        clearCartList();
        cartItemList = newCart;
    }

    public void clearCartList() {
        cartItemList = new ArrayList<>();
    }

    public List<DiscountResponse> getAllDiscounts() throws CustomException {
        List<DiscountResponse> list = new ArrayList<>();
        for (CartItem cart : cartItemList) {
            if (!cart.getProduct().getDiscount().isEmpty()) {
                for (Discount discount : cart.getProduct().getDiscount()) {
                    if (cart.getPrice() >= discount.getRequire_price()) {
                        DiscountResponse discountResponse = DiscountResponse.builder()
                                .id(discount.getId())
                                .description(discount.getDescription())
                                .stock(discount.getStock())
                                .name(discount.getName())
                                .promotion_price(discount.getPromotion_price())
                                .require_price(discount.getRequire_price())
                                .startDate(discount.getStartDate())
                                .endDate(discount.getEndDate())
                                .build();
                        list.add(discountResponse);
                    }
                }
            }
        }

        if (!list.isEmpty()) {
            return list;
        }

        throw new CustomException("There are no suitable discounts");
    }

    public List<CartItemResponse> findAll() {
        return cartItemList.stream()
                .map(c -> {
                    try {
                        return cartMapper.toResponse(c);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    public void save(CartItem cartItem) throws CustomException {
        CartItemResponse existingCartItem = findById(cartItem.getIdCart());
        if (existingCartItem == null) {
            cartItemList.add(cartItem);
        } else {
            int index = cartItemList.indexOf(existingCartItem);
            if (index != -1) {
                cartItemList.set(index, cartItem);
            }
        }
    }


    public CartItem addCart(Long idProduct) throws CustomException {
        Optional<Product> p = productRepository.findById(idProduct);
        if (p.isPresent()) {
            CartItem cartItem = findByIdProduct(idProduct);
            CartItem c = new CartItem();
            if (cartItem == null) {
                c.setIdCart(getNewId());
                c.setProduct(p.get());
                c.setPrice(p.get().getPrice());
                c.setQuantity(1);
                save(c);

            } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                save(cartItem);
            }
            return c;
        }
        throw new CustomException("Product not found");

    }

    public CartItemResponse findById(Long id) {
        for (CartItemResponse c : findAll()) {
            if (c.getIdCart().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public CartItem findByIdProduct(Long id) {
        for (CartItem c : cartItemList) {
            if (c.getProduct().getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Long getNewId() {
        Long idmax = 0L;
        for (CartItem c : cartItemList) {
            if (idmax < c.getIdCart()) {
                idmax = c.getIdCart();
            }
        }
        return idmax + 1;
    }

    public void deleteById(Long id) throws CustomException {
        CartItem cartItem = findByIdProduct(id);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            if (cartItem.getQuantity() <= 0) {
                // Xóa sản phẩm khỏi giỏ hàng nếu quantity <= 0
                cartItemList.remove(cartItem);
            } else {
                save(cartItem); // Chỉ lưu lại nếu quantity > 0
            }
        } else {
            throw new CustomException("CartItem not found");
        }
    }


}
