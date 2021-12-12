package com.daimler.boostbeast;

import io.swagger.codegen.*;
import io.swagger.models.properties.*;

import java.util.*;
import java.io.File;

public class CppBoostBeastClientCodegen extends DefaultCodegen implements CodegenConfig {
    public static final String CPP_NAMESPACE = "cppNamespace";
    public static final String CPP_NAMESPACE_DESC = "C++ namespace (convention: name::space::for::api).";
    protected final String PREFIX = "SWG";
    protected String cppNamespace = "Swagger::Model";

    // source folder where to write the files
    protected String sourceFolder = "src";
    protected String apiVersion = "1.0.0";

    /**
    * Configures the type of generator.
    *
    * @return  the CodegenType for this generator
    * @see     io.swagger.codegen.CodegenType
    */
    public CodegenType getTag() {
        return CodegenType.CLIENT;
    }

    /**
    * Configures a friendly name for the generator.  This will be used by the generator
    * to select the library with the -l flag.
    *
    * @return the friendly name for the generator
    */
    public String getName() {
        return "boostbeast";
    }

    /**
    * Returns human-friendly help for the generator.  Provide the consumer with help
    * tips, parameters here
    * 
    * @return A string value for the help message
    */
    public String getHelp() {
        return "Generates a boostbeast client library.";
    }

    public CppBoostBeastClientCodegen() {
        super();

        // set the output folder here
        outputFolder = "generated-code/boost-beast";

        /**
        * Models.  You can write model files using the modelTemplateFiles map.
        * if you want to create one template for file, you can do so here.
        * for multiple files for model, just put another entry in the `modelTemplateFiles` with
        * a different extension
        */
        modelTemplateFiles.put("model-header.mustache", ".h");       // the extension for each file to write

        /**
        * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
        * as with models, add multiple entries with different extensions for multiple files per
        * class
        */
        apiTemplateFiles.put("api-header.mustache", ".h");       // the extension for each file to write
        apiTemplateFiles.put("api-body.mustache", ".cpp");       // the extension for each file to write
        
        addOption(CPP_NAMESPACE, CPP_NAMESPACE_DESC, this.cppNamespace);
        /**
        * Template Location.  This is the location which templates will be read from.  The generator
        * will use the resource stream to attempt to read the templates.
        */
        templateDir = "boostbeast";

        /**
        * Api Package.  Optional, if needed, this can be used in templates
        */
        apiPackage = "io.swagger.client.api";

        /**
        * Model Package.  Optional, if needed, this can be used in templates
        */
        modelPackage = "io.swagger.client.model";

        /**
        * Reserved words.  Override this with reserved words specific to your language
        */
        reservedWords = new HashSet<String> (
            Arrays.asList(
                "alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept",
                "auto", "bitand", "bitor", "bool", "break", "case", "catch", "char", "char8_t", "char16_t", "char32_t",
                "class", "compl", "concept", "const", "consteval", "constexpr", "const_cast", "continue", "co_await",
                "co_return", "co_yield", "decltype", "default", "delete", "do", "double", "dynamic_cast", "else", 
                "enum", "explicit", "export", "extern", "false", "float", "for", "friend", "goto", "if", "inline", 
                "int", "import", "long", "module", "mutable", "namespace", "new", "noexcept", "not", "not_eq", "nullptr",
                "operator", "or", "or_eq", "private", "protected", "public", "register", "reinterpret_cast", "requires",
                "return", "short", "signed", "sizeof", "static", "static_assert", "static_cast", "struct", "switch",
                "synchronized", "template", "this", "thread_local", "throw", "true", "try", "typedef", "typeid", 
                "typename", "union", "unsigned", "using", "virtual", "void", "volatile", "wchar_t", "while", "xor",
                "xor_eq", "linux"
            )
        );

        super.typeMapping = new HashMap<String, String>();

        typeMapping.put("string", "std::string");
        typeMapping.put("integer", "int");
        typeMapping.put("float", "float");
        typeMapping.put("long", "long long");
        typeMapping.put("boolean", "bool");
        typeMapping.put("double", "double");
        typeMapping.put("array", "std::vector");
        typeMapping.put("map", "std::map");
        typeMapping.put("number", "long long");
        typeMapping.put("object", "VariantObject");
        typeMapping.put("binary", "std::vector<uint8_t>");
        typeMapping.put("password", "std::string");
        //TODO:Maybe use better formats for dateTime?
        typeMapping.put("file", "std::string");
        typeMapping.put("DateTime", "std::string");
        typeMapping.put("Date", "std::string");
        typeMapping.put("UUID", "std::string");
        
        /**
        * Additional Properties.  These values can be passed to the templates and
        * are available in models, apis, and supporting files
        */
        additionalProperties.put("apiVersion", apiVersion);
        
        /**
        * Supporting Files.  You can write single files for the generator with the
        * entire object tree available.  If the input file has a suffix of `.mustache
        * it will be processed by the template engine.  Otherwise, it will be copied
        */
        supportingFiles.add(new SupportingFile("CMakeLists.txt.mustache", sourceFolder, "CMakeLists.txt"));
        supportingFiles.add(new SupportingFile("object.mustache", sourceFolder, "VariantObject.h"));
        supportingFiles.add(new SupportingFile("HttpCommon.h.mustache", sourceFolder, "HttpCommon.h"));
        supportingFiles.add(new SupportingFile("HttpTask.h.mustache", sourceFolder, "HttpTask.h"));
        supportingFiles.add(new SupportingFile("Json.h.mustache", sourceFolder, "Json.h"));

        /**
        * Language Specific Primitives.  These types will not trigger imports by
        * the client generator
        */

        languageSpecificPrimitives = new HashSet<String>(
            Arrays.asList(
                "bool",
                "int",
                "long long",
                "float",
                "double",
                "std::string"
            )
        );
        defaultIncludes = new HashSet<String>(
            Arrays.asList(
                "bool",
                "int",
                "long long",
                "float",
                "double"
            )
        );
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey("cppNamespace")){
            cppNamespace = (String) additionalProperties.get("cppNamespace");
        }

        additionalProperties.put("cppNamespaceForMacros", cppNamespace.replace("::", "__"));
        additionalProperties.put("cppNamespaceDeclarations", cppNamespace.split("\\::"));
    }

    /**
    * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
    * those terms here.  This logic is only called if a variable matches the reserved words
    * 
    * @return the escaped term
    */
    @Override
    public String escapeReservedWord(String name) {
        if(this.reservedWordsMappings().containsKey(name)) {
            return this.reservedWordsMappings().get(name);
        }
        return "_" + name;  // add an underscore to the name
    }
    
    /**
    * Location to write model files.  You can use the modelPackage() as defined when the class is
    * instantiated
    */
    public String modelFileFolder() {
        return outputFolder + "/" + sourceFolder;
    }
    
    /**
    * Location to write api files.  You can use the apiPackage() as defined when the class is
    * instantiated
    */
    @Override
    public String apiFileFolder() {
        return outputFolder + "/" + sourceFolder;
    }

    /**
    * Optional - type declaration.  This is a String which is used by the templates to instantiate your
    * types.  There is typically special handling for different property types
    *
    * @return a string value used as the `dataType` field for model templates, `returnType` for api templates
    */
    @Override
    public String getTypeDeclaration(Property p) {
        String swaggerType = getSwaggerType(p);
        
        if (p instanceof ArrayProperty) {
            ArrayProperty ap = (ArrayProperty) p;
            Property inner = ap.getItems();
            System.out.println(ap.getName() + " " + inner.getName() + " " + getTypeDeclaration(inner));
            return getSwaggerType(p) + "<" + getTypeDeclaration(inner) + ">";
        } else if (p instanceof MapProperty) {
            MapProperty mp = (MapProperty) p;
            Property inner = mp.getAdditionalProperties();
            return getSwaggerType(p) + "<std::string, " + getTypeDeclaration(inner) + ">";
        }
        
        if (languageSpecificPrimitives.contains(swaggerType)) {
            return toModelName(swaggerType);
        } else {
            return swaggerType + "";
        }
    }

    /**
    * Optional - swagger type conversion.  This is used to map swagger types in a `Property` into 
    * either language specific types via `typeMapping` or into complex models if there is not a mapping.
    *
    * @return a string value of the type or complex model for this property
    * @see io.swagger.models.properties.Property
    */
    @Override
    public String getSwaggerType(Property p) {
        String swaggerType = super.getSwaggerType(p);
        String type = null;
        if(typeMapping.containsKey(swaggerType)) {
            type = typeMapping.get(swaggerType);
            if(languageSpecificPrimitives.contains(type))
            return toModelName(type);
        }
        else {
            type = swaggerType;
        }
        return toModelName(type);
    }

    @Override
    public void postProcessParameter(CodegenParameter parameter) {
        super.postProcessParameter(parameter);
    }

    @Override
    public String toModelName(String type) {
        if (typeMapping.keySet().contains(type) ||
        typeMapping.values().contains(type) ||
        importMapping.values().contains(type) ||
        defaultIncludes.contains(type) ||
        languageSpecificPrimitives.contains(type)) {
            return type;
        } else {
            return modelNamePrefix + Character.toUpperCase(type.charAt(0)) + type.substring(1).replace(".", "_");
        }
    }

    @Override
    public String toModelImport(String name) {
        if (name.equals("std::string")) {
            return "#include <string>";
        } else if (name.equals("std::map")) {
            return "#include <map>";
        } else if (name.equals("std::vector") || name.equals("std::vector<uint8_t>")) {
            return "#include <vector>";
        }
        return "#include \"" + name + ".h\"";
    }

    //Might not be needed
    @Override
    public String toDefaultValue(Property p) {
        if (p instanceof StringProperty) {
            return "{}";
        } else if (p instanceof BooleanProperty) {
            return "bool{false}";
        } else if (p instanceof DoubleProperty) {
            return "double{0}";
        } else if (p instanceof FloatProperty) {
            return "float{0.0f}";
        } else if (p instanceof IntegerProperty) {
            return "int{0}";
        } else if (p instanceof LongProperty) {
            return "long{0L}";
        } else if (p instanceof DecimalProperty) {
            return "long{0L}";
        } else if (p instanceof MapProperty) {
            return "{}";
        } else if (p instanceof ArrayProperty) {
            return "{}";
        }
        // else
        if (p instanceof RefProperty) {
            RefProperty rp = (RefProperty) p;
            return toModelName(rp.getSimpleRef()) + "{}";
        }
        return "null";
    }

    @Override
    public String toModelFilename(String name) {
        return modelNamePrefix + initialCaps(name).replace(".", "_");
    }

    @Override
    public String toApiName(String name) {
        return modelNamePrefix + initialCaps(name) + "Api";
    }

    @Override
    public String toApiFilename(String name) {
        return modelNamePrefix + initialCaps(name) + "Api";
    }

    @Override
    public String toVarName(String name) {
        name = sanitizeName(name);

        String paramName = name.replaceAll("[^a-zA-Z0-9_]", "");
        paramName = Character.toLowerCase(paramName.charAt(0)) + paramName.substring(1);
        if (isReservedWord(paramName)) {
            return escapeReservedWord(paramName);
        }
        return "" + paramName;
    }

    @Override
    public String toParamName(String name) {
        return toVarName(name);
    }

    @Override
    public String toOperationId(String operationId) {
        // throw exception if method name is empty
        if (operationId=="") {
            throw new RuntimeException("Empty method name (operationId) not allowed");
        }

        // method name cannot use reserved keyword, e.g. return$
        if (isReservedWord(operationId)) {
            operationId = escapeReservedWord(operationId);
        }

        // add_pet_by_id => addPetById
        return camelize(operationId, true);
    }
}
