package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;



public class SolrServiceTest {
	  private static final String DEFAULT_URL = "http://localhost:80801/solr/";
	public  void addFiled() throws SolrServerException, IOException{
		HttpSolrServer solrServer  = new HttpSolrServer(DEFAULT_URL);
		SolrInputDocument doc1 = new SolrInputDocument(); 
		doc1.addField("id", "001");
		doc1.addField("title", "上海一里网络科技");
		doc1.addField("IKType", "网上商城，便利店");
		doc1.addField("role", "项目经理");
		doc1.addField("account", "杜习虎");
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "002");
		doc2.addField("title", "百联集团");
		doc2.addField("IKType", "房地产，南京东路，华联超市，快客便利店，第一家超市");
		doc1.addField("role", "总经理");
		doc1.addField("account", "文剑，金凤");
		
		List<SolrInputDocument> docs = 	new ArrayList<SolrInputDocument>(); 
		docs.add(doc2);
		docs.add(doc1);
		solrServer.add(docs);
		solrServer.commit();
		
	}
	
	
	public void deleteIndex() throws SolrServerException, IOException{
		
		HttpSolrServer server = new HttpSolrServer(DEFAULT_URL);
		String q = "title:\"上海\"";
		UpdateResponse res = server.deleteByQuery(q);
		System.out.println(res.getStatus());
		server.commit(true, true);
		//或者使用server.deleteById(String id);以及deleteById(List<String> ids);

 

	}
	
	
	public void find() throws SolrServerException, IOException{
		HttpSolrServer server = new HttpSolrServer(DEFAULT_URL);
		
		SolrQuery query = new SolrQuery("上海");
		QueryResponse response = server.query(query);
		//文档方式读取，实际项目中如果业务比较复杂，采用这种方式显得比较灵活
		SolrDocumentList docs = response.getResults(); 
		System.out.println("文档个数：" + docs.getNumFound()); 
		System.out.println("查询时间：" + response.getQTime()); 
		for (SolrDocument doc : docs) { 
			System.out.println("id: " + doc.getFieldValue("id")); 
			System.out.println("title: " + doc.getFieldValue("title")); 
			System.out.println(); 
		}  
		//在上面的查询中还可以通过javabean的方式来获取查询结果：
		//List<Item> beans = response.getBeans(Item.class)
		//其中bean对象Item中定义字段与solr文档中的字段一一对应，对应设置multValued属性为true的字段类型，需要定义为list格式，否则response.getBeans(Item.class)
		//不能正确填充数据。
		
	}
}
