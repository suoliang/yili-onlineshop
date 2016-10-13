package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrTest {
	public static HttpSolrServer server;

	public static void main(String[] args) {
		String url = "http://192.168.1.135:8080/fushionbaby-solr/solr";
		server = new HttpSolrServer(url);
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", 12333L);
		doc1.addField("name", "Ë®¹þ¹þ ÎÒ°®¿µ Ê¦ ¸µ");
		doc1.addField("subject", "¿óÈªË®");
		 doc1.addField("memberBirthday_date", new Date());
		
		// SolrInputDocument doc2 = new SolrInputDocument();
		// doc2.addField("id", "id2", 1.0f);
		// doc2.addField("name", "doc2", 1.0f);
		// doc2.addField("price", 20);
		//
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		// docs.add(doc2);

		try {
			server.add(docs);
			//server.deleteByQuery("*is*");
			server.commit();
			UpdateRequest req = new UpdateRequest();
			req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
			// req.add(docs);
			UpdateResponse rsp = req.process(server);

			SolrQuery query = new SolrQuery();
			query.setQuery("(¿µÊ¦¸µ)");
			query.addSort("price", SolrQuery.ORDER.asc);
			QueryResponse res = server.query(query);
			SolrDocumentList dd = res.getResults();
			System.out.println(dd.size());
			Iterator<SolrDocument> all = dd.iterator();
			while (all.hasNext()) {
				SolrDocument dm = all.next();
				System.out.println(dm.getFieldNames());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
