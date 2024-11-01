package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers")
        method PUT()
        headers {
            contentType applicationJson()
        }
        body(
                [
                        id       : 1,
                        fullName : 'First Middle Last',
                        email    : 'example2@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '987654321'
                ]
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType: "application/json"
        }
        body(
                [
                        statusCode: 400,
                        customMessage: 'Passenger with email = example2@mail.com already exists'
                ]
        )
    }
}
