package com.ali.products.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ali.products.dao.ProductsDAO;
import com.ali.products.entitys.Product;


//expongo todos los resvicios rest que quiero que mi api tenga

/*a través de anotaciones puedo
 * exponer este simple método como
 * un servicio rest*/

@RestController
@RequestMapping("products")
public class ProductsREST {
	
	@Autowired
	private ProductsDAO productDAO;
	
	//método para resgresar objetos => ResponseEntity
	@GetMapping	
	public ResponseEntity<List<Product>> getProduct(){
		
		List<Product> products= productDAO.findAll();
		
		return ResponseEntity.ok(products);
	}
	
	@RequestMapping(value="{productId}")
	
	/*@PathVariable => le estoy diciendo que el parámetro que paso por @RequestMapping
	 * lo asigne a la propiedad del método (Long productId)*/	
		
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		
		//esto regresa un objeto de tipo Optional la cual es una clase que nos protege de un valor nulo		
		Optional<Product> optionalProduct = productDAO.findById(productId);
		
		//a través de Optional antes de recuperar el obj podemos saber si está presente
		if(optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());			
		}else {
			return ResponseEntity.noContent().build();
		}		
		
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productDAO.save(product);
		return ResponseEntity.ok(newProduct);
	} 
	
	
	@DeleteMapping(value="{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
		productDAO.deleteById(productId);		
		return ResponseEntity.ok(null);
	} 
	
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		
		Optional<Product> optionalProduct = productDAO.findById(product.getId());				
			//a través de Optional antes de recuperar el obj podemos saber si está presente
			if(optionalProduct.isPresent()) {
				Product updateProduct = optionalProduct.get();	
				updateProduct.setName(product.getName());
				
				//el save aplica para update e insert/create
				productDAO.save(updateProduct);
				
				return ResponseEntity.ok(updateProduct);
			}else {
				return ResponseEntity.notFound().build();
			}		
	} 
	
	//exponer método como servivio(2 formas)
	//@GetMapping (no define url sino que toma más bien la base o sea el @RequestMapping("/") de arriba)
	//@RequestMapping(value="hello", method=RequestMethod.GET) define una url en este caso (/hello)
	/*public String hello() {
		return "Hello world";
	}*/
}
