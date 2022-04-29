package com.github.jaxblib.commons.scl;

import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.dto.HeaderDTO;
import org.lfenergy.compas.sct.commons.exception.ScdException;
import org.lfenergy.compas.sct.commons.scl.SclRootAdapter;
import org.lfenergy.compas.sct.commons.scl.SclService;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

public class SclUtils {

    public byte[] getData(Integer numberofIED) throws ScdException, IOException {
        JavaJAXBUtilGeneric goodJAXBUtilGeneric = new JavaJAXBUtilGeneric();
        HeaderDTO headerDTO = new HeaderDTO(UUID.randomUUID(), "hv", "hr");
        SclRootAdapter sclRootAdapter = SclService.initScl(Optional.empty(), headerDTO.getVersion(), headerDTO.getRevision());
        SCL scd = sclRootAdapter.getCurrentElem();
        for (int i = 0; i < numberofIED; i++) {
            File resource = new ClassPathResource("PERF/basic-7MB.xml").getFile();
            SCL icd = goodJAXBUtilGeneric.unmarshal(SCL.class, Files.readAllBytes(resource.toPath()));
            SclService.addIED(scd, "IED"+i, icd);
        }
        return goodJAXBUtilGeneric.marshal(scd) ;
    }

    public SCL getSCLObject(Integer numberofIED) throws ScdException, IOException {
        JavaJAXBUtilGeneric goodJAXBUtilGeneric = new JavaJAXBUtilGeneric();

        HeaderDTO headerDTO = new HeaderDTO(UUID.randomUUID(), "hv", "hr");
        SclRootAdapter sclRootAdapter = SclService.initScl(Optional.empty(), headerDTO.getVersion(), headerDTO.getRevision());
        SCL scd = sclRootAdapter.getCurrentElem();
        for (int i = 0; i < numberofIED; i++) {
            File resource = new ClassPathResource("PERF/basic-7MB.xml").getFile();
            SCL icd = goodJAXBUtilGeneric.unmarshal(SCL.class, Files.readAllBytes(resource.toPath()));
            SclService.addIED(scd, "IED"+i, icd);
        }
        return scd ;
    }
}
