package ma.omnishore.clients.service;

import java.util.List;

import ma.omnishore.clients.domain.Client;

public interface IClientService {

	/**
	 * Save a client.
	 * @param client the entity to save.
	 * @return the persisted entity.
	 */
	Client createClient(Client client);

	/**
	 * Get all the clients.
	 * @return the list of entities.
	 */
	List<Client> getAllClients();

	/**
	 * Get one client by id.
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Client getClient(Long id);

	/**
	 * Update a client.
	 * @param client  the entity to update.
	 * @return the updated entity.
	 */
	public Client updateClient(Client client);

	/**
	 * Delete the client by id.
	 * @param id the id of the entity.
	 */
	void deleteClient(Long id);
}
