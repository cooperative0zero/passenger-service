package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers/1")
        method DELETE()
    }

    response {
        status NO_CONTENT()
        headers {
            contentType: applicationJson()
        }
    }
}
