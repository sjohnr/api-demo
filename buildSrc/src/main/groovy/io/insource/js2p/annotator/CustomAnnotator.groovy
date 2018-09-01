package io.insource.js2p.annotator

import com.fasterxml.jackson.databind.JsonNode
import com.sun.codemodel.JClass
import com.sun.codemodel.JDefinedClass
import com.sun.codemodel.JFieldVar
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.jsonschema2pojo.AbstractAnnotator

class CustomAnnotator extends AbstractAnnotator {
    @Override
    void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        def annotation = clazz.annotate(ApiModel)
        if (schema.has("description")) {
            annotation.param("description", schema.get("description").textValue())
        }
    }

    @Override
    void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        def annotation = field.annotate(ApiModelProperty).param("name", propertyName)
        if (propertyNode.has("description")) {
            annotation.param("value", propertyNode.get("description").textValue())
        }
        if (propertyNode.has("required") && propertyNode.get("required").booleanValue()) {
            annotation.param("required", true)
        }
    }
}
