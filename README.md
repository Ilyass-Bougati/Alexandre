# ALEXANDRE
Alexandre is a modular e-commerce platform built with a microservices architecture. It features:
- **Product Catalog & Checkout:** Customers can browse products, add to cart, and complete purchases through a modern frontend.
- **Inventory Management:** Real-time stock tracking across multiple warehouses, with automated low-stock alerts and invoice-linked operations.
- **Admin Dashboard:** Role-based access for admins and staff to manage products, view reports, and monitor inventory health.
- **Microservices Architecture:** This includes service discovery, configuration server, authentication and authorization, and decoupled domain services (catalog, inventory, orders, and notifications).
- **Observability-First:** Integrated metrics with Prometheus, logs with Loki, and distributed traces via OpenTelemetry + Tempo, all visualized through Grafana dashboards.
- **Secure by Design:** JWT-based authentication, RBAC, and service-level communication over secure channels.
- **DevOps Friendly:** Fully containerized with Docker Compose, supports local development and production deployment.

This project demonstrates scalable backend architecture, real-time data synchronization, and production-grade observability for enterprise-level commerce and logistics applications.
