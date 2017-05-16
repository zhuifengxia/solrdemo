package com.jwu.solr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import com.csvreader.CsvReader;

public class AddIndexFromCsv {

	/**
	 * @param args
	 */
	private static final String SOLR_URL = "http://115.28.40.100:8080/solr/mediskin";

	public SolrServer getSolrServer() throws MalformedURLException {
		return new HttpSolrServer(SOLR_URL);
	}

	public static void main(String[] args) {
		try {
			AddIndexFromCsv importer = new AddIndexFromCsv();
			Object[][] data = importer.readCsv("data/SkinLib_Literature.csv");
			// importer.printData(data);

			SolrServer server = importer.getSolrServer();
			server.deleteByQuery("*:*");
			importer.addIndex(server, data);
			
			System.out.println("Insert success.");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object[][] readCsv(String csvFilePath) throws Exception {
		CsvReader reader = new CsvReader(csvFilePath, ',',
				Charset.forName("GBK"));
		reader.readHeaders();
		String[] headers = reader.getHeaders();

		List<Object[]> list = new ArrayList<Object[]>();
		while (reader.readRecord()) {
			list.add(reader.getValues());
		}
		Object[][] data = new String[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			data[i] = list.get(i);
		}
		return data;
	}

	private void printData(Object[][] data) {
		for (int i = 0; i < data.length; i++) {
			Object[] record = data[i];
			for (int j = 0; j < record.length; j++) {
				Object cell = record[j];
				System.out.print(cell + "\t");
			}
			System.out.println("");
		}
	}

	private void addIndex(SolrServer server, Object[][] data) throws SolrServerException, IOException {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
		for (int i=0; i<data.length; i++) {
			Object[] record = data[i];
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", UUID.randomUUID());
			doc.addField("articleType", "文献");
			doc.addField("articleId", record[0]);
			doc.addField("magzine", record[4]);
			doc.addField("number", record[2]);
			doc.addField("title", record[1]);
			doc.addField("summary", record[5]);
			doc.addField("author", record[3]);
			doc.addField("content", record[7]);
			//doc.addField("addDate", record[9]);
			//doc.addField("updateDate", record[10]);
			docs.add(doc);
		}
		
		UpdateRequest req = new UpdateRequest();
		req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		req.add(docs);
		UpdateResponse rsp = req.process(server);
	}
}
