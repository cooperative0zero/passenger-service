package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers/999")
        method DELETE()
    }

    response {
        status NOT_FOUND()
        headers {
            contentType: applicationJson()
        }
        body(
                [
                        statusCode: 404,
                        customMessage: 'Passenger with id = 999 not exists'
                ]
        )
    }
}
