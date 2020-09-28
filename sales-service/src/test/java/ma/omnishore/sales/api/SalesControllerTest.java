package ma.omnishore.sales.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

import ma.omnishore.sales.api.SalesController;
import ma.omnishore.sales.domain.Sale;
import ma.omnishore.sales.impl.SalesService;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class SalesControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private SalesController controller;
	@MockBean
	private SalesService service;

	private final Gson gson = new Gson();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void testGetsales() throws Exception {
		Sale sale1 = new Sale("Code1", 1L, new Date(), 3L, 10.0D);
		Sale sale2 = new Sale("Code2", 2L, new Date(), 2L, 20.0D);
		when(service.getAllSales()).thenReturn(Stream.of(sale1, sale2).collect(Collectors.toList()));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/sales").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2))).andReturn();
	}

	@Test
	public void testGetSale() throws Exception {
		Sale sale1 = new Sale("Code1", 1L, new Date(), 3L, 10.0D);
		sale1.setId(1L);
		when(service.getSale(1L)).thenReturn(sale1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/sales/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		JSONAssert.assertEquals(gson.toJson(sale1), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetSaleByClient() throws Exception {
		Sale sale1 = new Sale("Code1", 1L, new Date(), 3L, 10.0D);
		when(service.getSalesByClient(1L)).thenReturn(Stream.of(sale1).collect(Collectors.toList()));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/sales/client/1").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2))).andReturn();

	}

	@Test
	public void testCreateSale() throws Exception {
		Sale sale1 = new Sale("Code1", 1L, new Date(), 3L, 10.0D);
		when(service.createSale(any())).thenReturn(sale1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/sales").accept(MediaType.APPLICATION_JSON).content(gson.toJson(sale1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();

		JSONAssert.assertEquals(gson.toJson(sale1), result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testDeleteSale() throws Exception {
		doNothing().when(service).deleteSale(1L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/sales/1").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

}
