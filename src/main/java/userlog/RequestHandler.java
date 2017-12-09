package userlog;

import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

public class RequestHandler extends RequestHandlerBase {

    @Override
    public void handleRequestBody(SolrQueryRequest solrQueryRequest, SolrQueryResponse solrQueryResponse) throws Exception {
        Recommendation recommendation = new Recommendation();
        String id = solrQueryRequest.getParams().get("id");
        String recommendedKeywords =  recommendation.abc(id);
        solrQueryResponse.addResponse(recommendedKeywords);
    }

    @Override
    public String getDescription() {
        return "The request handler returns recommendations based on similarity between user views";
    }
}
