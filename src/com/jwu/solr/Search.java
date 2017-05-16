package com.jwu.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Search {

	private static final String SOLR_URL = "http://115.28.40.100:8080/solr/mediskin";

	public SolrServer getSolrServer() throws MalformedURLException {
		return new HttpSolrServer(SOLR_URL);
	}

	public void SolrJSearch() {
		try {
			SolrServer solrServer = getSolrServer();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void search() throws MalformedURLException {
		SolrServer solrServer = getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery("*.*");
		query.addFilterQuery("articleType:试题");

		try {
			QueryResponse rsp = solrServer.query(query);

			SolrDocumentList docs = rsp.getResults();
			System.out.println("文档个数：" + docs.getNumFound());
			System.out.println("查询时间：" + rsp.getQTime());
			for (SolrDocument doc : docs) {
				String title = (String) doc.getFieldValue("title");
				String id = (String) doc.getFieldValue("id");
				System.out.println(id);
				System.out.println(title);
				System.out.println(doc.getFieldValue("content"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		Search sj = new Search();
		sj.search();
	}
}