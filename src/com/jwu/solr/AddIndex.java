package com.jwu.solr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class AddIndex {
	private static final String SOLR_URL = "http://115.28.40.100:8080/solr/mediskin";

	public SolrServer getSolrServer() throws MalformedURLException {
		return new HttpSolrServer(SOLR_URL);
	}

	public void commit() throws SolrServerException, IOException {
		SolrServer server = getSolrServer();
		server.deleteByQuery("*:*");
		
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "cc02b090-68a2-11e3-bfd0-000c297acaed");
		doc1.addField("articleType", "试题");
		doc1.addField("articleId", "1");
		doc1.addField("url", "mediskin.cn");
		doc1.addField("title", "夏季皮炎");
		doc1.addField("content", "夏季皮炎是一种皮肤病");
		//doc1.addField("addDate", "10/11/2013");
		
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "b8c9bfb6-68a5-11e3-b802-000c297acaed");
		doc2.addField("articleType", "试题");
		doc2.addField("articleId", "2");
		doc2.addField("url", "mediskin.cn");
		doc2.addField("title", "肺炎");
		doc2.addField("content", "肺炎是一种呼吸道疾病");
		//doc2.addField("addDate", "2013-12-19T08:47:00.717Z");

		SolrInputDocument doc3 = new SolrInputDocument();
		doc3.addField("id", "d98ba1e6-68a6-11e3-8915-000c297acaed");
		doc3.addField("articleType", "新闻");
		doc3.addField("articleId", "1");
		doc3.addField("url", "mediskin.cn");
		doc3.addField("title", "中国好医生");
		doc3.addField("content", "中国好医生活动现在开始");
		//doc3.addField("addDate", "2013-12-19T08:47:00.717Z");

		SolrInputDocument doc4 = new SolrInputDocument();
		doc4.addField("id", "e23e43f2-68a6-11e3-8e93-000c297acaed");
		doc4.addField("articleType", "新闻");
		doc4.addField("articleId", "2");
		doc4.addField("url", "mediskin.cn");
		doc4.addField("title", "中国好医生");
		doc4.addField("content", "中国好医生在沈阳");
		//doc4.addField("addDate", "2013-12-19T08:47:00.717Z");

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		docs.add(doc2);
		docs.add(doc3);
		docs.add(doc4);
		
		UpdateRequest req = new UpdateRequest();
		req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		req.add(docs);
		UpdateResponse rsp = req.process(server);
	}

	public static void main(String[] args) throws SolrServerException,
			IOException {
		AddIndex ui = new AddIndex();
		ui.commit();
		System.out.println("Insert success.");
	}
}