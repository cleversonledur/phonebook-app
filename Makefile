# Install openapi-generator-cli

# Install openapi-generator-cli
install-openapi-generator-cli:
	@echo "Installing openapi-generator-cli"
	@curl -L https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/5.0.0/openapi-generator-cli-5.0.0.jar -o openapi-generator-cli.jar

# Generate documentation
generate-openapi-docs:
	@echo "Generating documentation"
	@java -jar openapi-generator-cli.jar generate -i ./openapi.yml -g html2 -o docs


# Generate all
generate-docs: install-openapi-generator-cli generate-openapi-docs

# build the spring boot app in /backend
build-backend:
	@echo "Building backend"
	@cd backend && ./mvnw clean package

# build the react app in /frontend
build-frontend:
	@echo "Building frontend"
	@cd frontend && npm run build

# build the docker image with docker-compose
build-docker:
	@echo "Building docker image"
	@docker-compose build

# run the docker image with docker-compose
run-docker:
	@echo "Running docker image"
	@docker-compose up

# build the app and docker image
build: build-backend build-frontend build-docker

# run the app and docker image
run: run-docker

# build and run the app and docker image
build-run: build run