# Mintyn Coding Test

This project is an Android application designed to fetch to BIN (Bank Identification Number) information using a RESTful API. It also demonstrate the usage of OCR to extract information from debit/credit cards. The application is built using the modern Android development stack, utilizing Kotlin, Jetpack Compose for the UI, and the clean architecture pattern. 

## Table of Contents
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Unit Testing](#unit-testing)

## Project Structure
The project is organized in a clean and modular structure:

- **`app`**: Contains the main Android application module.
  - **`MainActivity`**: The entry point of the application.
  - **`CardInput`**: Composable function for capturing card input and launching OCR.
  - **`CardWithDetails`**: Composable function displaying details from the BIN API response.
  - **`CardDetailRow`**: Composable function for displaying details in a row.
  - **`OCRActivity`**: Activity for capturing or loading an image and extracting text using OCR.

- **`data`**: Contains data-related components.
  - **`repository`**: Holds the repository responsible for fetching BIN information.
  - **`network`**: Defines Retrofit setup and API service interface.
  
- **`presentation`**: Contains components related to UI presentation.
  - **`viewmodel`**: Houses the `BinDataViewModel`, responsible for managing UI-related data and business logic.

- **`utils`**: Includes utility functions, such as OCRUtils for processing images with OCR.

- **`test`**: Holds test classes for unit testing.

## Usage
1. Launch the application on an Android device or emulator.
2. Enter a card number in the designated field.
3. Press the "Fetch BIN Info" button to retrieve information from the BIN API.
4. Optionally, press the "OCR" button to use OCR functionality to extract text from an image.

## Dependencies
- **Kotlin**: The programming language used for the Android application.
- **Jetpack Compose**: Modern UI toolkit for building native Android applications.
- **Retrofit**: HTTP client for making network requests.
- **Gson**: JSON serialization/deserialization library for Kotlin.
- **OCR**: Tesseract and Leptonica library for Optical Character Recognition.

## Unit Testing
Unit tests are implemented using JUnit and the Mockito testing framework. The `BinDataViewModelTest` class includes tests for various scenarios related to fetching BIN information.

