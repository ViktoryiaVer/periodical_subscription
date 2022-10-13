/*CREATE DATABASE periodicalSubscription;
DROP TABLE IF EXISTS users;*/
CREATE TABLE  IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    role VARCHAR(70) NOT NULL,
    avatar TEXT,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

/*DROP TABLE IF EXISTS periodicals;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS periodicals_categories  */
/*CREATE TABLE  IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);*/

CREATE TABLE  IF NOT EXISTS periodicals (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    publication_date DATE NOT NULL,
    issues_amount_in_year INT NOT NULL,
    price DECIMAL(6, 2) NOT NULL,
    language VARCHAR(70) NOT NULL,
    image TEXT NOT NULL,
    type VARCHAR(70) NOT NULL,
    status VARCHAR(30) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS periodicals_categories (
    id BIGSERIAL PRIMARY KEY,
    periodical_id BIGINT NOT NULL REFERENCES periodicals,
    category VARCHAR(255) NOT NULL
);

--DROP TABLE IF EXISTS subscriptions;
CREATE TABLE  IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users,
    total_cost DECIMAl(8, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

--DROP TABLE IF EXISTS subscription_details;
CREATE TABLE  IF NOT EXISTS subscription_details (
    id BIGSERIAL PRIMARY KEY,
    subscription_id BIGINT NOT NULL REFERENCES subscriptions,
    periodical_id BIGINT NOT NULL REFERENCES periodicals,
    subscription_duration_in_years INT NOT NULL,
    periodical_current_price DECIMAl(6, 2) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
--DROP TABLE IF EXISTS payments;
CREATE TABLE  IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users,
    subscription_id BIGINT NOT NULL REFERENCES subscriptions,
    payment_time TIMESTAMP NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);