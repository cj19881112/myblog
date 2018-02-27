package com.cj;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.Gson;

public class RestHelper {
	protected TestRestTemplate rest;

	public RestHelper(TestRestTemplate rest) {
		this.rest = rest;
	}

	public <T> ResponseEntity<T> getForEntity(String url, ParameterizedTypeReference<T> typeDef) {
		return getForEntity(url, new HashMap<>(), typeDef);
	}

	public <T> ResponseEntity<T> getForEntity(String url, Map<String, Object> params,
			ParameterizedTypeReference<T> typeDef) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(params), headers);
		return rest.exchange(url, HttpMethod.GET, entity, typeDef);
	}

	public <T> ResponseEntity<T> postForEntity(String url, ParameterizedTypeReference<T> typeDef) {
		return postForEntity(url, new HashMap<>(), typeDef);
	}

	public <T> ResponseEntity<T> postForEntity(String url, Map<String, Object> params,
			ParameterizedTypeReference<T> typeDef) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();
		for (String key : params.keySet()) {
			formParams.add(key, params.get(key));
		}
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(formParams, headers);
		return rest.exchange(url, HttpMethod.POST, entity, typeDef);
	}

	public <T> ResponseEntity<T> postForEntity(String url, String params, ParameterizedTypeReference<T> typeDef) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(params, headers);
		return rest.exchange(url, HttpMethod.POST, entity, typeDef);
	}
}
