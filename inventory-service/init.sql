CREATE TABLE inventory(
    id SERIAL PRIMARY KEY,
    productId BIGINT UNIQUE,
    available INT,
    updatedAt TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS outbox (
  id SERIAL PRIMARY KEY,
  aggregate_type VARCHAR(100),
  aggregate_id BIGINT,
  type VARCHAR(100),
  payload JSONB,
  created_at TIMESTAMP DEFAULT now(),
  processed BOOLEAN DEFAULT FALSE
);