package com.shop.Admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.shop.Admin.DAO.ProducerDAO;
import com.shop.models.Producer;


@ManagedBean(name="producerBean")
@SessionScoped
public class ProducerBean implements Serializable {
	private ProducerDAO producerService = new ProducerDAO();
	private Producer new_producer = new Producer();
	private Producer current_producer;
	private List<Producer> listProducer;
	public ProducerDAO getProducerService() {
		return producerService;
	}
	public void setProducerService(ProducerDAO producerService) {
		this.producerService = producerService;
	}
	public Producer getNew_producer() {
		return new_producer;
	}
	public void setNew_producer(Producer new_producer) {
		this.new_producer = new_producer;
	}
	public Producer getCurrent_producer() {
		return current_producer;
	}
	public void setCurrent_producer(Producer current_producer) {
		this.current_producer = current_producer;
	}
	public List<Producer> getListProducer() {
		return listProducer;
	}
	public void setListProducer(List<Producer> listProducer) {
		this.listProducer = listProducer;
	}
	public List<Producer> getProducers()
	{
		listProducer = producerService.getListProducer();
		return listProducer;
	}
	public Map<String, Integer> getDropdownProducer()
	{
		return producerService.getDropdownProducer();
	}
}
