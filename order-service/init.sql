-- order DB init: orders table + outbox
CREATE TABLE IF NOT EXISTS orders (
  id SERIAL PRIMARY KEY,
  status VARCHAR(50) NOT NULL,
  total NUMERIC(12,2),
  user_id BIGINT,
  created_at TIMESTAMP DEFAULT now()
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
