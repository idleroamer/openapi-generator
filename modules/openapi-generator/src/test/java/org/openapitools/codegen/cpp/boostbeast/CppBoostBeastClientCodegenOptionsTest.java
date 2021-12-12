package org.openapitools.codegen.cpp.beast;

import org.openapitools.codegen.AbstractOptionsTest;
import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.languages.CppBeastClientCodegen;
import org.openapitools.codegen.options.CppBeastClientCodegenOptionsProvider;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CppBeastClientCodegenOptionsTest extends AbstractOptionsTest {
    private CppBeastClientCodegen codegen = mock(CppBeastClientCodegen.class, mockSettings);

    public CppBeastClientCodegenOptionsTest() {
        super(new CppBeastClientCodegenOptionsProvider());
    }

    @Override
    protected CodegenConfig getCodegenConfig() {
        return codegen;
    }

    @SuppressWarnings("unused")
    @Override
    protected void verifyOptions() {
        // TODO: Complete options using Mockito
        // verify(codegen).someMethod(arguments)
    }
}

