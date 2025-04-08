CREATE TABLE customers
(
    id       BIGINT       NOT NULL,
    name     VARCHAR(255) NULL,
    surname  VARCHAR(255) NULL,
    email    VARCHAR(255) NULL,
    shopcart VARCHAR(255) NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE sales
(
    id          BIGINT NOT NULL,
    customer_id BIGINT NULL,
    drug_id     BIGINT NULL,
    quantity    INT    NULL,
    total_price DOUBLE NULL,
    CONSTRAINT pk_sales PRIMARY KEY (id)
);

CREATE TABLE workers
(
    id            BIGINT       NOT NULL,
    name          VARCHAR(255) NULL,
    surname       VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    monthly_sales BIGINT       NULL,
    salary        DOUBLE       NULL,
    CONSTRAINT pk_workers PRIMARY KEY (id)
);