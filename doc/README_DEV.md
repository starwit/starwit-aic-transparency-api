

```json
{
  "name": "Super AnDe",
  "description": "Description",
  "version": "0.0.1",
  "applicationIdentifier": "traffic",  
  "useAI": true,
  "model": {
    "name": "Model",
    "version": "1.1.1",
    "lastDeployment": "2025-04-21T15:01:04.476Z",
    "modelType": "LLM",
    "modelLink": "https://example.com/",
    "publicTrainingData": true,
    "linkToPublicTrainingData": "https://example.com/"
  },
  "actionTypes": [],
  "decisionTypes": [],
  "sBOMLocation": {
    "backend": {
      "url": "http://localhost:8080/sbom-samples/aic-sbom-backend.json"
    }
  },
  "successors": []
}

{
  "name": "People Counter",
  "description": "Description",
  "version": "0.0.1",
  "applicationIdentifier": "traffic",
  "useAI": true,
  "model": {
    "name": "Model",
    "version": "1.1.1",
    "lastDeployment": "2025-04-21T15:01:04.476Z",
    "modelType": "LLM",
    "modelLink": "https://example.com/",
    "publicTrainingData": true,
    "linkToPublicTrainingData": "https://example.com/"
  },
  "actionTypes": [],
  "decisionTypes": [],
  "sBOMLocation": {
    "backend": {
      "url": "http://localhost:8080/sbom-samples/aic-sbom-backend.json"
    }
  },
  "successors": [{"name": "Super AnDe"}]
}

{
  "name": "SpeedingDetector",
  "description": "Description",
  "version": "0.0.1",
  "applicationIdentifier": "traffic",
  "useAI": true,
  "model": {
    "name": "Model",
    "version": "1.1.1",
    "lastDeployment": "2025-04-21T15:01:04.476Z",
    "modelType": "LLM",
    "modelLink": "https://example.com/",
    "publicTrainingData": true,
    "linkToPublicTrainingData": "https://example.com/"
  },
  "actionTypes": [],
  "decisionTypes": [],
  "sBOMLocation": {
    "backend": {
      "url": "http://localhost:8080/sbom-samples/aic-sbom-backend.json"
    }
  },
  "successors": []
}
```