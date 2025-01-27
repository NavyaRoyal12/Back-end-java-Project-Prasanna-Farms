package in.prasannaFarm.controller;


	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import in.prasannaFarm.entity.CartTable;
	import in.prasannaFarm.service.CartService;
	@RestController
	@RequestMapping("/cart")
	public class CartController {
	    @Autowired
	    private CartService cartService;
	    // Get all cart items for a specific user
	    @GetMapping("/{userId}")
	    public ResponseEntity<List<CartTable>> getCartItems(@PathVariable long userId) {
	        List<CartTable> cartItems = cartService.getCartItemsByUserId(userId);
	        return ResponseEntity.ok(cartItems);
	    }
	    // Add a product to the cart
	    @PostMapping("/add")
	    public ResponseEntity<CartTable> addToCart(@RequestBody CartTable cartItem) {
	        CartTable addedItem = cartService.addToCart(cartItem);
	        return ResponseEntity.ok(addedItem);
	    }
	    // Remove a product from the cart
	    @DeleteMapping("/remove/{cartItemId}")
	    public ResponseEntity<Void> removeFromCart(@PathVariable long cartItemId) {
	        cartService.removeFromCart(cartItemId);
	        return ResponseEntity.noContent().build();
	    }
	    // Clear all cart items for a specific user
	    @DeleteMapping("/clear/{userId}")
	    public ResponseEntity<Void> clearCart(@PathVariable long userId) {
	        cartService.clearCart(userId);
	        return ResponseEntity.noContent().build();
	    }
	}
