package ma.omnishore.clients.api.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ma.omnishore.clients.dto.SaleDto;

@FeignClient(name = "sales-service")
public interface SalesClient {

	@GetMapping("/api/sales/client/{id}")
	List<SaleDto> getClientSales(@PathVariable("id") Long clientId);
}