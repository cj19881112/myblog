package com.cj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	private final Map<String, String> cookies = new HashMap<>();

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
		headers.put("Cookie", Arrays.asList(new String[] { getCookiesString() }));
		HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(params), headers);
		ResponseEntity<T> resp = rest.exchange(url, HttpMethod.GET, entity, typeDef);
		extractCookies(resp.getHeaders());
		return resp;
	}

	public <T> ResponseEntity<T> postForEntity(String url, ParameterizedTypeReference<T> typeDef) {
		return postForEntity(url, new HashMap<>(), typeDef);
	}

	public <T> ResponseEntity<T> postForEntity(String url, Map<String, Object> params,
			ParameterizedTypeReference<T> typeDef) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.put("Cookie", Arrays.asList(new String[] { getCookiesString() }));

		MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();
		for (String key : params.keySet()) {
			formParams.add(key, params.get(key));
		}
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(formParams, headers);

		ResponseEntity<T> resp = rest.exchange(url, HttpMethod.POST, entity, typeDef);
		extractCookies(resp.getHeaders());

		return resp;
	}

	public <T> ResponseEntity<T> postForEntity(String url, String params, ParameterizedTypeReference<T> typeDef) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.put("Cookie", Arrays.asList(new String[] { getCookiesString() }));
		HttpEntity<String> entity = new HttpEntity<>(params, headers);
		ResponseEntity<T> resp = rest.exchange(url, HttpMethod.POST, entity, typeDef);
		extractCookies(resp.getHeaders());

		return resp;
	}

	private String getCookiesString() {
		StringBuilder sb = new StringBuilder();
		if (!cookies.isEmpty()) {
			cookies.entrySet().forEach(entry -> {
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(entry.getValue());
				sb.append(";");
			});
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private void extractCookies(HttpHeaders headers) {
		List<String> cookiesList = headers.get("Set-Cookie");
		if (cookiesList != null && !cookiesList.isEmpty()) {
			String cookiesStr = cookiesList.get(0);
			String[] cookiesSplit = cookiesStr.split(";");
			for (String cr : cookiesSplit) {
				String[] keyValueSplit = cr.split("=");
				if (keyValueSplit.length == 2) {
					cookies.put(keyValueSplit[0], keyValueSplit[1]);
				}
			}
		}
	}
}
