package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers/1")
        method GET()
    }

    response {
        status OK()
        headers {
            contentType: applicationJson()
        }
        body(
            [
                    id       : 1,
                    fullName : 'First Middle Last',
                    email    : 'example@mail.com',
                    rating   : 1f,
                    isDeleted: false,
                    phone    : '987654321'
            ]
        )
    }
}
