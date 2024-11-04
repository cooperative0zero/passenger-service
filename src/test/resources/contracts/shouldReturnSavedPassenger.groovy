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
                        email    : 'exampleTestMail@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '123456'
                ]
        )
    }

    response {
        status CREATED()
        headers {
            contentType: applicationJson()
        }
        body(
                [
                        id       : 3,
                        fullName : 'First Middle Last',
                        email    : 'exampleTestMail@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '123456'
                ]
        )
    }
}
