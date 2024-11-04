package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers")
        method POST()
        headers {
            contentType applicationJson()
        }
        body(
                [
                        fullName : 'First Middle Last',
                        email    : 'example@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '987654321'
                ]
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType: applicationJson()
        }
        body(
                [
                        statusCode: 400,
                        customMessage: 'Passenger with email = example@mail.com already exists'
                ]
        )
    }
}
